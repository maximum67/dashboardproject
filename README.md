# dashboardproject
веб-приложение визуализации данных для управления предприятием
для визуализации данные по расписанию отправляются из учетной системы 1С на FTP сервер (не входит в функционал приложения) в виде xls файлов
в приложении с помощью java spring security организован доступ к данным и настройкам пользователей, доступ к ftp серверу, расписание парсинга файлов с данными
пользователи имею возможность индивидуально настраивать под себя - какие данные должны отображаться, за какой период, в виде каких графиков и диаграмм
количество данных не ограничено, возможно объединение в группы, всё настраивает администратор
построение графиков и диаграмм реализовано с помощью highcharts.js
все данные, а также настройки доступа к ftp, настройки парсинга, настройки пользователей храняться в MySQL


data visualization web application for enterprise management 
for visualization, scheduled data is sent from the 1C accounting system to an FTP server (not included in the application functionality) in the form of xls files 
in the application using java spring security, access to user data and settings is organized, access to an ftp server, a schedule for parsing files with data 
users have the opportunity individually customize for yourself - what data should be displayed, for what period, in the form of which graphs and diagrams
the amount of data is unlimited, it is possible to combine into groups, everything is configured by the administrator.
the construction of graphs and diagrams is implemented using highcharts.js 
all data, as well as ftp access settings, parsing settings, user settings are stored in MySQL
