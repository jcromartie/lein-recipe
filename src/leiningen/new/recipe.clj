(ns leiningen.new.recipe
  "Generate a new Clojure Cookbook recipe"
  (:import java.io.File)
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [leiningen.new.templates :refer [renderer ->files]]))

(defn title->path
  "Returns filesystem-appropriate name for recipe"
  [title]
  (-> title
      str/lower-case
      (str/replace #"[^\w]+" "-")))

(def render (renderer "recipe"))

(defn recipe
  "A template for Clojure Cookbook recipes

  usage: lein new recipe chapter \"Tile of Your Recipe\""
  [basedir title]
  (let [basename (title->path title)
        dir (File. (str basedir "/" basename))
        filename (str basename ".asciidoc")
        file (File. dir filename)
        data {:title title}]
    (if (.mkdirs dir)
      (spit file (render "recipe.asciidoc" data))
      (println "File already exists"))))
