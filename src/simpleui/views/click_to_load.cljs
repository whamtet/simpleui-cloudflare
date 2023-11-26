(ns simpleui.views.click-to-load
    (:require-macros
      [ctmx.core :as ctmx :refer [defcomponent]]
      [simpleui.example :refer [defexample]]))

;; snippet
(def src "0123456789ABCDEF")
(defn rand-str []
  (clojure.string/join (repeatedly 15 #(rand-nth src))))

(defn tr [i]
    [:tr [:td "Agent Smith"] [:td (str "void" i "@null.org")] [:td (rand-str)]])

(defcomponent ^:endpoint rows-click [req ^:long page]
  (list
    (map tr (range (* 10 page) (* 10 (inc page))))
    [:tr {:id id}
      [:td {:colspan "3"}
        [:button.btn
          {:hx-get "rows-click"
           :hx-target (hash ".")
           :hx-vals {:page (inc page)}}
           "Load More Agents..."
           [:img.htmx-indicator {:src "../../bars.svg"}]]]]))

(defexample
  "/click-to-load-handler"
  (fn [req]
    [:table
      [:thead
        [:tr [:th "Name"] [:th "Email"] [:th "ID"]]]
      [:tbody (rows-click req 0)]]))
;; snippet
