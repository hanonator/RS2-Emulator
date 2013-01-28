INSERT INTO character_equipment (item_id, amount, character_id, slot)
SELECT * FROM (SELECT ? AS k, ? AS j, ? AS i, ? AS H) AS tmp
WHERE NOT EXISTS (
    SELECT character_equipment.character_id, character_equipment.slot
	FROM character_equipment
	WHERE character_equipment.character_id=? AND character_equipment.slot=?
) LIMIT 1;
