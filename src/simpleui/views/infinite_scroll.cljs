(ns simpleui.views.infinite-scroll
  (:require ctmx.core)
  (:require-macros
    [ctmx.core :as ctmx :refer [defcomponent]]
    [simpleui.example :refer [defexample]]))

;; snippet
(def src "0123456789ABCDEF")
(defn rand-str []
  (clojure.string/join (repeatedly 15 #(rand-nth src))))

(defn tr [i]
    [:tr
      (when (= 9 (mod i 10))
        {:hx-get "rows" :hx-trigger "revealed" :hx-swap "afterend" :hx-vals {:page (inc i)}})
      [:td "Agent Smith"]
      [:td (str "void" i "@null.org")]
      [:td (rand-str)]])

(defcomponent ^:endpoint rows [req ^:long page]
  (map tr (range page (+ 10 page))))

(defexample
  "/infinite-scroll-handler"
  (fn [req]
    [:table
      [:thead
        [:tr [:th "Name"] [:th "Email"] [:th "ID"]]]
      [:tbody (rows req 0)]]))
;; snippet
