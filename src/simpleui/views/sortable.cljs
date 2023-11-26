(ns simpleui.views.sortable
    (:require-macros
      [ctmx.core :as ctmx :refer [defcomponent]]
      [simpleui.example :refer [defexample]]))

;; snippet
(defn- content [items]
  (list*
    [:div.htmx-indicator "Updating..."]
    (for [item items]
      [:div
        [:input {:type "hidden" :name "order" :value item}]
        "Item " item])))

(defcomponent ^:endpoint sortable [req ^:ints order]
  (if (not-empty order)
    (content order)
    [:form#to-sort {:hx-post "sortable" :hx-trigger "end"}
      (content (range 1 6))]))

(defexample
  "/sortable-handler"
  (fn [req]
    (sortable req nil)))
;; snippet
