(ns simpleui.adapter
	(:require
		[promesa.core :as p]))

(defn- iterator-seq [entries]
	(->> #(.-value (.next entries))
			 repeatedly
			 (take-while identity)))

(defn- vectorize [v]
	(if (vector? v) v [v]))
(defn- assoc-list [m k v]
	(if (contains? m k)
		(assoc m k (-> k m vectorize (conj v)))
		(assoc m k v)))
(defn get-body [req]
	(p/let [body (.text req)]
		(->> body
				 js/URLSearchParams.
				 .entries
				 iterator-seq
				 (reduce
					(fn [m [k v]]
						(assoc-list m (keyword k) v)) {}))))

(defn get-query [query]
	(when query
		(into {}
					(for [k (js/Object.keys query)]
						[(keyword k) (aget query k)]))))

(defn get-headers [headers]
	(->> headers .keys iterator-seq (map #(vector % (.get headers %))) (into {})))

(defn ->req [req]
	(p/let [body (get-body req)]
		{:params (-> req .-query get-query (merge body))
		 :headers (-> req .-headers get-headers)
		 :uri (-> req .-url js/URL. .-pathname)
		 :request-method (-> req .-method .toLowerCase keyword)}))

(defn <-req [{:keys [body status headers]}]
	(->> {:status status
				:headers (or headers {})}
			 clj->js
			 (js/Response. body)))
