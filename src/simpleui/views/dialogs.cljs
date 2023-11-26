(ns simpleui.views.dialogs
  (:require ctmx.core)
  (:require-macros
    [ctmx.core :as ctmx :refer [defcomponent]]
    [simpleui.example :refer [defexample]]))

;; snippet
(defcomponent ^:endpoint reply [{:keys [headers]}]
  [:div#response.mmargin "You entered " (headers "hx-prompt")])

(defexample
  "/dialogs-handler"
  (fn [req]
    reply
    [:div
      [:button.btn.mb
        {:hx-post "reply"
         :hx-prompt "Enter a string"
         :hx-confirm "Are you sure?"
         :hx-target "#response"}
        "Prompt Submission"]
      [:div#response]]))
;; snippet
