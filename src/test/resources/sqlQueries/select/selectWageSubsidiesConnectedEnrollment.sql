﻿SELECT COUNT(*) FROM WAGE_SUBSIDY ws WHERE ws.ATAA_RTAA_ENROLLMENT_ID = (SELECT ID FROM ATAA_RTAA_ENROLLMENT are WHERE are.TRADE_ENROLLMENT_ID = (SELECT ID FROM TRADE_ENROLLMENT te WHERE te.PARTICIPANT_ID = (SELECT ID FROM PARTICIPANT p WHERE p.FIRST_NAME='%1$s')))