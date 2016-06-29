(ns kata.twitter-oauth
  (:require [oauth.client :as oauth]
            [kata.config :refer [env]]
            [mount.core :refer [defstate]]
            [clojure.tools.logging :as log]))


;;have to set Callback URL in Twitter app settings

(def request-token-uri
  "https://api.twitter.com/oauth/request_token")

(def access-token-uri
  "https://api.twitter.com/oauth/access_token")

(def authorize-uri
  "https://api.twitter.com/oauth/authenticate")

(defstate consumer
          :start
          (oauth/make-consumer (env :twitter-consumer-key)
                               (env :twitter-consumer-secret)
                               request-token-uri
                               access-token-uri
                               authorize-uri
                               :hmac-sha1))

(defn oauth-callback-uri
  "Generates the twitter oauth request callback URI"
  [{:keys [headers]}]
  (str (or (headers "x-forwarded-proto")
           (env :protocol)
           "https")
       "://"
       (headers "host")
       "/oauth/twitter-callback"))

(defn fetch-request-token
  "Fetches a request token."
  [request]
  (let [callback-uri (oauth-callback-uri request)]
    (log/info "Fetching request token from twitter using callback-uri" callback-uri)
    (oauth/request-token consumer (oauth-callback-uri request))))

(defn fetch-access-token
  [request_token]
  (oauth/access-token consumer request_token (:oauth_verifier request_token)))

(defn auth-redirect-uri
  "Gets the URI the user should be redirected to when authenticating with twitter."
  [request-token]
  (str (oauth/user-approval-uri consumer request-token)))
