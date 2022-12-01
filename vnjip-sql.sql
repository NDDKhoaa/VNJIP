/* Role */
INSERT INTO `vnjip`.`role` (`role_number`, `role_name`) VALUES ('1', 'Admin');
INSERT INTO `vnjip`.`role` (`role_number`, `role_name`) VALUES ('2', 'Agent');
INSERT INTO `vnjip`.`role` (`role_number`, `role_name`) VALUES ('3', 'Client');
/* Account Status */
INSERT INTO `vnjip`.`accountstatus` (`account_status_short`, `account_status_desc`) VALUES ('A', 'Active');
INSERT INTO `vnjip`.`accountstatus` (`account_status_short`, `account_status_desc`) VALUES ('T', 'Terminated');
/* Account Type */
INSERT INTO `vnjip`.`accounttype` (`account_type_short`, `account_type_desc`) VALUES ('A', 'Agent');
INSERT INTO `vnjip`.`accounttype` (`account_type_short`, `account_type_desc`) VALUES ('B', 'Broker');
INSERT INTO `vnjip`.`accounttype` (`account_type_short`, `account_type_desc`) VALUES ('C', 'Coinsurer');
INSERT INTO `vnjip`.`accounttype` (`account_type_short`, `account_type_desc`) VALUES ('R', 'Reinsurer');
/* Billing Currency */
INSERT INTO `vnjip`.`billingcurrency` (`currency_short`, `currency_name`) VALUES ('VND', 'Vietnam Dong');
INSERT INTO `vnjip`.`billingcurrency` (`currency_short`, `currency_name`) VALUES ('USD', 'US Dollar');
INSERT INTO `vnjip`.`billingcurrency` (`currency_short`, `currency_name`) VALUES ('SGD', 'Singapore Dollar');
/* Country */
INSERT INTO `vnjip`.`country` (`country_short`, `country_name`) VALUES ('VNI', 'Vietnam');
INSERT INTO `vnjip`.`country` (`country_short`, `country_name`) VALUES ('USA', 'United State');
INSERT INTO `vnjip`.`country` (`country_short`, `country_name`) VALUES ('SIN', 'Singapore');
INSERT INTO `vnjip`.`country` (`country_short`, `country_name`) VALUES ('MAL', 'Malaysia');
/* Gender */
INSERT INTO `vnjip`.`gender` (`gender_short`, `gender_name`) VALUES ('M', 'Male');
INSERT INTO `vnjip`.`gender` (`gender_short`, `gender_name`) VALUES ('F', 'Female');
INSERT INTO `vnjip`.`gender` (`gender_short`, `gender_name`) VALUES ('U', 'Unknown');
/* Marital Status */
INSERT INTO `vnjip`.`maritalstatus` (`marital_short`, `marital_status`) VALUES ('S', 'Single');
INSERT INTO `vnjip`.`maritalstatus` (`marital_short`, `marital_status`) VALUES ('M', 'Married');
INSERT INTO `vnjip`.`maritalstatus` (`marital_short`, `marital_status`) VALUES ('D', 'Divorced');
/* Policy Status */
INSERT INTO `vnjip`.`policy_status` (`policy_status_short`, `policy_status_desc`) VALUES ('PN', 'Pending');
INSERT INTO `vnjip`.`policy_status` (`policy_status_short`, `policy_status_desc`) VALUES ('IF', 'In force');
/* Admin Account */
INSERT INTO `vnjip`.`account` (`account_number`, `email`, `password`, `username`, `account_status_short`) VALUES ('1', 'admin@gmail.com', '123456', 'admin', 'A');
INSERT INTO `vnjip`.`account_roles` (`account_account_number`, `roles_role_number`) VALUES ('1', '1');
