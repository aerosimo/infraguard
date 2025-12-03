<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="Elijah Omisore" name="author">
    <meta content="Aerosimo 1.0.0" name="generator">
    <meta content="Aerosimo" name="application-name">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="Aerosimo IT Consultancy" name="description">
    <meta content="Aerosimo" name="apple-mobile-web-app-title">
    <meta content="Oracle, Java, Tomcat, Maven, Jenkins, Bitbucket, Github" name="keywords">
    <meta content="width=device-width, initial-scale=1, user-scalable=no" name="viewport" />
    <!-- Title -->
    <title>InfraGuard | Aerosimo Ltd</title>
    <!-- Favicon-->
    <link href="assets/img/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Google Fonts for clean typography -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        /* Reset and base */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #1e1e2f; /* dark background */
            color: #f0f0f0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .container {
            text-align: center;
        }

        .logo {
            width: 180px;
            height: auto;
            margin-bottom: 40px;
            filter: drop-shadow(0 0 10px #4d3b7a);
        }

        h1 {
            font-size: 2.5rem;
            margin-bottom: 10px;
            color: #ffffff;
            text-shadow: 0 0 5px #4d3b7a;
        }

        p {
            font-size: 1.1rem;
            color: #b0b0b0;
        }

        /* Optional dark-themed button */
        .btn {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 30px;
            background-color: #4d3b7a;
            color: #fff;
            font-weight: 500;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
            text-decoration: none;
        }

        .btn:hover {
            background-color: #6c52a1;
            transform: translateY(-2px);
        }

        /* Subtle animated gradient background */
        body::before {
            content: "";
            position: fixed;
            top: 0; left: 0; right: 0; bottom: 0;
            background: linear-gradient(120deg, #2c2c3e, #1e1e2f, #2c2c3e);
            z-index: -1;
            animation: gradientShift 15s ease infinite;
        }

        @keyframes gradientShift {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }
    </style>
</head>
<body>
<div class="container">
    <img src="https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png" alt="InfraGuard Logo" class="logo">
    <h1>Welcome to InfraGuard</h1>
    <p>infrastructure guard, is a protective branding that essentially describe a Java-based system monitoring and health-check application, that aggregates server metrics (uptime, load average, hostname, connections), resource usage (CPU, memory, disk), and service checks (HTTP/TCP).</p>
    <a href="#" class="btn">Get Started</a>
</div>
</body>
</html>