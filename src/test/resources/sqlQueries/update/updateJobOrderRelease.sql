﻿UPDATE JOB_ORDER SET DATE_NON_VET_RELEASE='%1$s' WHERE JOB_TITLE='%2$s' AND EMPLOYER_ID=(SELECT ID FROM EMPLOYER WHERE EMPLOYER_NAME='%3$s')