﻿UPDATE SERVICE_ENROLLMENT SET DATE_RESULT='2100-01-01' WHERE EMPLOYER_ID=(SELECT ID FROM EMPLOYER WHERE EMPLOYER_NAME='%1$s')