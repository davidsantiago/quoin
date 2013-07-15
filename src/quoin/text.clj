(ns quoin.text
  "Functions that process text.")

(defn html-escape
  "HTML-escapes the given string."
  [^String s]
  ;; This method is "Java in Clojure" for serious speedups.
  (let [sb (StringBuilder.)
        slength (long (count s))]
    (loop [idx (long 0)]
      (if (>= idx slength)
        (.toString sb)
        (let [c (char (.charAt s idx))]
          (case c
            \& (.append sb "&amp;")
            \< (.append sb "&lt;")
            \> (.append sb "&gt;")
            \" (.append sb "&quot;")
            \™ (.append sb "&trade;")
            \é (.append sb "&eacute;")
            (.append sb c))
          (recur (inc idx)))))))

(defn indent-string
  "Given a String s, indents each line by inserting the string indentation
   at the beginning."
  [^String s ^String indentation]
  (let [str+padding (StringBuilder.)]
    (loop [start-idx 0
           next-idx (.indexOf s "\n")] ;; \n handles both \r\n & \n linebreaks.
      (if (= -1 next-idx)
        ;; We've reached the end. If the start and end are the same, don't
        ;; indent before an empty string. Either way, return the string. 
        (do (when (not= start-idx (count s))
              (.append str+padding indentation)
              (.append str+padding s start-idx (count s)))
            (.toString str+padding))
        (let [next-idx (inc next-idx)]
          (.append str+padding indentation)
          (.append str+padding s start-idx next-idx)
          (recur next-idx (.indexOf s "\n" next-idx)))))))
