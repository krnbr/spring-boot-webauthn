@import gg.jte.Content

@param String title
@param Content content
@param org.springframework.security.web.csrf.CsrfToken csrfToken
@param String username

<!DOCTYPE html>
<html lang="en" data-theme="emerald">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="/imgs/favicon_0101.png">
    <script src="https://unpkg.com/htmx.org@2.0.3"></script>
    <link href="/css/styles.css" rel="stylesheet" type="text/css" />
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css" rel="stylesheet" type="text/css" />
    <title>${title}</title>
</head>
<body class="font-sans">
<div class="flex flex-col min-h-screen mx-auto container">
    <div class="navbar bg-base-100 shadow-2xl">
        <div class="flex-1">
            <a class="btn btn-ghost text-xl">Welcome, ${username}!</a>
        </div>
        <div class="flex-none">
            <ul class="menu menu-horizontal px-1">
                <li>
                    <a href="/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="grid-rows-4 grid-flow-col gap-4 flex-grow">
        <main>
            ${content}
        </main>
    </div>
    <br/>
    <footer class="footer text-content p-5 footer-center bottom-0 p-4">
        <aside>
            <p>Copyright © - 01101011</p>
        </aside>
    </footer>
</div>
</body>
</html>