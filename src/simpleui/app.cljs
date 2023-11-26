(ns simpleui.app
	(:require
		[promesa.core :as p]
		[simpleui.adapter :refer [->req <-req]]
		[simpleui.views.hello :as hello]))

(defn ^:export handler [req]
	(p/let [req (->req req)]
		(try
			(<-req {:status 200
					    :body (pr-str (hello/ui-routes))})
			(catch :default e
				(-> e str js/Response.)))))
