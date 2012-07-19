(ns quoin.test.map-access
  (:use clojure.test
        quoin.map-access))

(deftest test-contains-named?
  (is (= :a
         (contains-named? {:a 1} :a)))
  (is (= :a
         (contains-named? {:a 1} "a")))
  (is (= "a"
         (contains-named? {"a" 1} :a)))
  (is (= "a"
         (contains-named? {"a" 1} "a")))
  (is (= 1
         (contains-named? {:a 2} "b" 1))))

(deftest test-get-named
  (is (= "success"
         (get-named {"test" "success"} "test")))
  (is (= "success"
         (get-named {"test" "success"} :test)))
  (is (= "success"
         (get-named {:test "success"} :test)))
  (is (= "success"
         (get-named {:test "success"} "test")))
  (is (= "failure"
         (get-named {:test "success"} "TEST" "failure"))))

(deftest test-assoc-named
  (is (= {:a 1}
         (assoc-named {:a 0} :a 1)))
  (is (= {:a 1}
         (assoc-named {:a 0} "a" 1)))
  (is (= {"a" 1}
         (assoc-named {"a" 0} :a 1)))
  (is (= {"a" 1}
         (assoc-named {"a" 0} "a" 1)))
  (is (= {:a 1 :b 2}
         (assoc-named {:b 0} :a 1 "b" 2))))

(deftest test-dissoc-named
  (is (= {}
         (dissoc-named {"test" 1} "test")))
  (is (= {}
         (dissoc-named {"test" 1} :test)))
  (is (= {}
         (dissoc-named {"test1" 1 :test2 2} :test1 "test2"))))
