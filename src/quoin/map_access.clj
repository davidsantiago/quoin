(ns quoin.map-access
  "Routines to examine and modify maps, counting keys that have the same name
   as identical (ie, :blah and \"blah\" identify the same key).")

(defn contains-named?
  "Given a map and a key, returns \"true\" if the map contains the key, allowing
   for differences of type between string and keyword. That is, :blah and
   \"blah\" are the same key. The key of the same type is preferred. Returns
   the variant of the found key for true, nil for false."
  ([map key] (contains-named? map key nil))
  ([map key not-found]
     (if (contains? map key)
       key
       (if (keyword? key)
         (let [str-key (name key)]
           (if (contains? map str-key)
             str-key not-found))
         (let [kw-key (keyword key)]
           (if (contains? map kw-key)
             kw-key not-found))))))

(defn get-named
  "Given a map and a key, gets the value out of the map, trying various
   permitted combinations of the key. Key can be either a keyword or string,
   and is tried first as it is, before being converted to the other."
  ([map key]
     (get-named map key nil))
  ([map key not-found]
     (or (get map key)
         (get map (keyword key))
         (get map (name key))
         not-found)))

(defn assoc-named
  "Just like clojure.core/assoc, except considers keys that are
   keywords and strings equivalent. That is, if you
   assoc-named :keyword into a map with a key \"keyword\", the latter
   is replaced."
  ([map key val]
     (let [found-key (contains-named? map key key)]
       (assoc map found-key val)))
  ([map key val & kvs]
     (let [new-map (assoc-named map key val)]
       (if kvs
         (recur new-map (first kvs) (second kvs) (nnext kvs))
         new-map))))

(defn dissoc-named
  "Given a map and key(s), returns a map without the mappings for the keys,
   allowing for the keys to be certain combinations (ie, string/keyword are
   equivalent)."
  ([map] map)
  ([map key]
     (if-let [found-key (contains-named? map key)]
       (dissoc map found-key)))
  ([map key & ks]
     (let [new-map (dissoc-named map key)]
       (if ks
         (recur new-map (first ks) (next ks))
         new-map))))