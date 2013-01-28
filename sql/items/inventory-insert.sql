INSERT INTO character_inventory (item_id, amount, character_id, slot)
SELECT * FROM (SELECT ? AS k, ? AS j, ? AS i, ? AS H) AS tmp
WHERE NOT EXISTS (
    SELECT character_inventory.character_id, character_inventory.slot
	FROM character_inventory
	WHERE character_inventory.character_id=? AND character_inventory.slot=?
) LIMIT 1;
