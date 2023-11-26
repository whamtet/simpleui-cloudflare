(ns simpleui.views.bulk-update
    (:require-macros
      [ctmx.core :as ctmx :refer [defcomponent defn-parse]]
      [simpleui.example :refer [defexample]]))

;; snippet
(def init-data
  [{:name "Joe Smith" :email "joe@smith.org" :status "Inactive"}
   {:name "Angie MacDowell" :email "angie@macdowell.org" :status "Inactive"}
   {:name "Fuqua Tarkenton" :email "fuqua@tarkenton.org" :status "Inactive"}
   {:name "Kim Yee"	:email "kim@yee.org"	:status "Inactive"}])

(defn- set-status [status data i]
  (update data i assoc :status status))

(defn-parse update-data [{:keys [^:edn data ^:longs ids status]} _]
  {:ids (set ids)
   :data (reduce (partial set-status status) data ids)
   :status status})

(defn tr [ids action i {:keys [name email status]}]
  [:tr {:class (when (contains? ids i) action)}
   [:td [:input {:type "checkbox" :name "ids" :value i :checked (contains? ids i)}]]
   [:td name]
   [:td email]
   [:td status]])

(defcomponent ^:endpoint ^{:params update-data} update-form [req ids ^:json data status]
  [:form {:id id :hx-target "this"}
   [:input {:type "hidden" :name "data" :value (pr-str data)}]
   [:table
    [:thead
     [:tr [:th] [:th "Name"] [:th "Email"] [:th "Status"]]]
    [:tbody (map-indexed (partial tr ids status) data)]]
   [:button.mmargin
    {:hx-put "update-form"
     :hx-vals {:status "Active"}}
    "Activate"]
   [:button.mmargin
    {:hx-put "update-form"
     :hx-vals {:status "Inactive"}}
    "Deactivate"]])

(defexample
  "/bulk-update-handler"
  (fn [req]
    (update-form req #{} init-data nil)))
;; snippet
