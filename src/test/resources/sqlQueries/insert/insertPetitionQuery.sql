﻿INSERT INTO T_PETITION(PETITION_ID,EMPLOYER_ID,OTHER_STATE_EMPLOYER_NAME, PETITION_NUMBER, CERTIFICATION_DATE) SELECT NEXT VALUE FOR SEQ_T_PETITION, EMPLOYER_ID, EMPLOYER_NAME, '%1$s', '%3$s' FROM T_EMPLOYER WHERE EMPLOYER_NAME='%2$s'