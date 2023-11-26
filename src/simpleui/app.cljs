(ns simpleui.app
	(:require
		[promesa.core :as p]
		[reitit.ring :as r]
		[simpleui.adapter :refer [->req <-req]]
		[simpleui.views.hello :as hello]))

(def reitit-handler
	(-> hello/ui-routes r/router r/ring-handler))

(defn ^:export handler [req]
	(p/let [req (->req req)]
		(try
			(-> req reitit-handler <-req)
			(catch :default e
				(-> e str js/Response.)))))
