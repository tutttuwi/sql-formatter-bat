SELECT
  *,
  a,
  b,
  c
FROM
  test_table AS TEST(NOLOCK)
  LEFT JOIN fruit AS F(nolock) ON F.key = TEST.key
GROUP BY
  KEY
ORDER BY
  KEY;

INSERT INTO
  test.a.b
VALUES
  (1, 2, 3);

/*
DELETE TEST
*/
DELETE FROM
  test.a.b
WHERE
  delFlg = 1;

/*
UPDATE TEST
*/
UPDATE
  test
SET
  name = 'patch'
WHERE
  patchFlg = 1;

/*
SELECT TEST
*/
SELECT
  *
FROM
  test_table AS TEST(NOLOCK)
  LEFT JOIN fruit AS F(nolock) ON F.key = TEST.key
GROUP BY
  KEY
ORDER BY
  KEY;