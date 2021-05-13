CREATE TABLE currencies (
                              id bigint NOT NULL,
                              effective_date varchar(255) DEFAULT NULL,
                              number varchar(255) DEFAULT NULL,
                              table_name varchar(255) DEFAULT NULL,
                              time_stamp datetime DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `exchange` (
                            `id` bigint NOT NULL,
                            `amount` double DEFAULT NULL,
                            `amount_to_return` double DEFAULT NULL,
                            `from_currency` varchar(255) DEFAULT NULL,
                            `time_stamp` datetime DEFAULT NULL,
                            `to_currency` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE rate (
                        `id` bigint NOT NULL,
                        `code` varchar(255) DEFAULT NULL,
                        `currency` varchar(255) DEFAULT NULL,
                        `mid` decimal(19,2) DEFAULT NULL,
                        `currencies_id` bigint,
                        PRIMARY KEY (`id`),
                        FOREIGN KEY (`currencies_id`) REFERENCES currencies(id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
