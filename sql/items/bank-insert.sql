INSERT INTO character_bank (item_id, amount, character_id, slot)
SELECT * FROM (SELECT ? AS k, ? AS j, ? AS i, ? AS H) AS tmp
WHERE NOT EXISTS (
    SELECT character_bank.character_id, character_bank.slot
	FROM character_bank
	WHERE character_bank.character_id=? AND character_bank.slot=?
) LIMIT 1;
