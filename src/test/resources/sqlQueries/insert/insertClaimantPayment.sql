﻿INSERT INTO T_CLAIMANT_PAYMENT_OPTION (CLAIMANT_PAYMENT_OPTION_ID, CLAIMANT_ID, FEDERAL_TAX_STATUS, STATE_TAX_STATUS, PAYMENT_MODE, DEBIT_CARD_STATUS, BANK_ACCOUNT_NUMBER, BANK_ROUTING_NUMBER, BANK_ACCOUNT_TYPE, NAME_ON_BANK_ACCOUNT, STATUS_DATE, UPDATED_TS, UPDATED_BY, DEBIT_CARD_PROGRAM_NUMBER) SELECT NEXTVAL FOR SEQ_T_CLAIMANT_PAYMENT_OPTION, CLAIMANT_ID, '0', null, 'C', null, 'ACC', 'ROUTE', 'TP', 'NAME', null, '2012-08-13 15:11:11.0', 'AUTOMATION', 'DEBIT' FROM T_CLAIMANT WHERE SSN = '%1$s'