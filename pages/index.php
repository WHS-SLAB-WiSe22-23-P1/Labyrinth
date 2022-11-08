<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <title>Dokumentation</title>
</head>
<body>
    <h1>Dokumentation</h1>
    <nav>
    <?php
        foreach(array_diff(scandir("."), array('.', '..', 'index.php')) as $file) {
            echo "<a href=\"$file\">$file</a>\n";
        }
    ?>
    </nav>
</body>
</html>