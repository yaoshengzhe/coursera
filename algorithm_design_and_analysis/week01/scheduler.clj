(require '[clojure.java.io :as io])


(def lines (with-open [rdr (io/reader "job.txt")]
             (line-seq rdr)))

(def total_line (first lines))


(println (take lines total_line))