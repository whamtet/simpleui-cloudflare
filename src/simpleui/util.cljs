(ns simpleui.util
    (:require
      [clojure.pprint]))

(defn pprint [x]
  (with-out-str (clojure.pprint/pprint x)))
