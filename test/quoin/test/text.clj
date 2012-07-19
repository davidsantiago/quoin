(ns quoin.test.text
  (:use clojure.test
        quoin.text))

(deftest test-html-escape
  (is (= "&lt;script&gt;"
         (html-escape "<script>")))
  (is (= "&amp;lt;script&amp;gt;"
         (html-escape (html-escape "<script>"))))
  (is (= "&lt;script src=&quot;blah.js&quot;&gt;"
         (html-escape "<script src=\"blah.js\">"))))

(deftest test-indent-string
  (is (= " blah\n blah")
      (indent-string "blah\nblah" " "))
  (is (= " blah\r\n blah"
         (indent-string "blah\r\nblah" " ")))
  (is (= " blah"
         (indent-string "blah" " ")))
  ;; Shouldn't indent a non-existing last line when string ends on \n.
  (is (= " blah\n"
         (indent-string "blah\n" " "))))