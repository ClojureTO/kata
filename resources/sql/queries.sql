-- :name add-example! :! :n
-- :doc add an exmaple
INSERT INTO problems
(title, description, code)
VALUES (:title, :description, :code)


-- :name get-examples :? :*
-- :doc get examples
SELECT * FROM problems