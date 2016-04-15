-- :name add-example! :! :n
-- :doc add an exmaple
INSERT INTO problems
(title, submitter, description, code)
VALUES (:title, :submitter, :description, :code)

-- :name get-examples :? :*
-- :doc get examples
SELECT * FROM problems
