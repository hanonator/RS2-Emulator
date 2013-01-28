INSERT INTO items (item_id, amount)
SELECT * FROM (SELECT ?, ?) AS tmp
WHERE NOT EXISTS (
    SELECT * FROM items WHERE item_id=? AND amount=?
)
LIMIT 1
