use taskflow;
DELIMITER //
    SET CLOBAL event_scheduler = ON
    CREATE EVENT update_token_of_day
    ON SCHEDULE EVERY 1 DAY
   STARTS TIMESTAMP(CURRENT_DATE, '00:00:00')
    DO
        BEGIN
           UPDATE app_user user
            SET user.token_per_day_available = 2

        END//
   CREATE EVENT update_token_of_month
        ON SCHEDULE
   EVERY 1 MONTH
   STARTS TIMESTAMP(CURRENT_DATE, '00:00:00')
    DO
        BEGIN
            UPDATE app_user user
            SET user.token_per_month_available = 2
        END//
DELIMITER;