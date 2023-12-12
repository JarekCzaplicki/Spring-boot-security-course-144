insert into users(id, username, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
values
    (1, 'jarek' ,'$2a$10$uN5rqxJlftstwdHt3bZNH.OiY.eWGrRI9Mnmd7jPhFDQxH2.pRMEW',1,1,1,1),
    (2, 'daniel' ,'$2a$10$.G2ab8ena7gqDXEqvn9bWeid1ONr8yhKb9epp9AGi8dDXdCsvLBKS',1,1,1,1),
    (3, 'marek' ,'$2a$10$BMkCbf7VnSYuymv3xhxcte.fPSl0RRrTLxlNYkhB.6/EOHL2.vvsW',1,1,1,1),
    (4, 'kasia' ,'$2a$10$musxm0A/En7hSN4drNzX6..VUrCaasNIKtu/qLn6jhUttSjpb5hxm',1,1,1,1);

insert into authority(id, authority)
values
    (1, 'STUDENT'),
    (2, 'ADMIN'),
    (3, 'ADMINTRAINEE'),
    (4, 'GUEST');


insert into  user_authorities(user_id, authority_id)
values
    (1, 2),
    (2, 3),
    (3, 1),
    (4,4);