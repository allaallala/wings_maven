﻿UPDATE ASSESSMENT a SET a.SCORE = %1$d WHERE a.PARTICIPANT_ID = (SELECT ID FROM PARTICIPANT p WHERE p.FIRST_NAME = '%2$s')