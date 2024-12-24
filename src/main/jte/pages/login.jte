@param String username
@param org.springframework.security.web.csrf.CsrfToken csrfToken
@param String csrfHeader
@param String contextPath

@template.layouts.mtLayout(title = "index", content = @`
    <section class="grid text-center h-screen items-center p-8">
        <div>
            <h3 class="block antialiased tracking-normal font-sans text-3xl font-semibold leading-snug text-blue-gray-900 mb-2">
                Sign In
            </h3>
            <p class="block antialiased font-sans text-base font-light leading-relaxed text-inherit mb-8 text-gray-600 font-normal text-[18px]">
                Enter your username(or email) and password to sign in
            </p>
            <form class="mx-auto max-w-[24rem] text-left" method="post" action="/login">
                <div class="mb-6">
                    <label for="username" class="block mb-2 text-sm text-slate-600">
                        Enter username
                    </label>
                    <input id="user" name="username" placeholder="Username or Email" type="text" class="w-full bg-transparent placeholder:text-slate-400 text-slate-700 text-sm border border-slate-200 rounded-md px-3 py-2 transition duration-300 ease focus:outline-none focus:border-blue-500 hover:border-blue-300 shadow-sm focus:shadow" />
                </div>
                <div class="mb-6">
                    <label for="password" class="block mb-2 text-sm text-slate-600">
                        Enter password
                    </label>
                    <input id="password" name="password" placeholder="Password" type="password" class="w-full bg-transparent placeholder:text-slate-400 text-slate-700 text-sm border border-slate-200 rounded-md px-3 py-2 transition duration-300 ease focus:outline-none focus:border-blue-500 hover:border-blue-300 shadow-sm focus:shadow" />
                </div>
                <div class="mb-6">
                    <input type="hidden"
                           name="${csrfToken.getParameterName()}"
                           value="${csrfToken.getToken()}"/>
                    <input type="hidden" name="contextPath" value="${contextPath}"/>
                    <input type="hidden" name="csrfHeaders" value="${csrfHeader}"/>
                    <button
                            class="align-middle select-none font-sans font-bold text-center uppercase transition-all disabled:opacity-50 disabled:shadow-none disabled:pointer-events-none text-sm py-3.5 px-7 rounded-lg bg-gray-900 text-white shadow-md shadow-gray-900/10 hover:shadow-lg hover:shadow-gray-900/20 focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none bg-gray-900 block w-full gap-3 mt-6"
                            type="submit"
                            data-ripple-light="true">
                        sign in
                    </button>
                </div>
                <div class="!mt-4 flex justify-end">
                    <a
                            href="/reset-password"
                            class="block antialiased font-sans text-sm font-light leading-normal text-blue-gray-900 font-medium"
                    >Forgot password</a
                    >
                </div>
                <div class="divider">OR</div>
                <button
                        class="align-middle select-none font-sans font-bold text-center uppercase transition-all disabled:opacity-50 disabled:shadow-none disabled:pointer-events-none text-sm py-3.5 px-7 rounded-lg border border-gray-900 text-gray-900 hover:opacity-75 focus:ring focus:ring-gray-300 active:opacity-[0.85] block w-full gap-3 mt-6 flex h-12 items-center justify-center gap-2"
                        type="button"
                        data-ripple-dark="false"
                        id="passkey-signin"
                >
                    <img
                            src="/imgs/pk.png"
                            alt="passkey"
                            class="h-12 w-12"
                    />
                    <!-- -->Sign in with a passkey
                </button>
                <p
                        class="block antialiased font-sans text-sm font-light leading-normal text-gray-700 !mt-4 text-center font-normal"
                >
                    Not registered?<!-- -->
                    <a href="/signup" class="font-medium text-gray-900">Create account</a>
                </p>
            </form>
        </div>
        <footer class="w-full bg-white p-8">
            <div class="divider mx-auto max-w-[24rem]">Copyright © 2024 01101011.</div>
        </footer>
        <script type="text/javascript" src="js/webauthn.js"></script>
        <script type="text/javascript">
            let csrfHeader = JSON.parse("${csrfHeader}");
            document.addEventListener("DOMContentLoaded",() => setupLogin(csrfHeader, "${contextPath}", document.getElementById('passkey-signin')));
        </script>
    </section>
`)