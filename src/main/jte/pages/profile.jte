@param String username
@param org.springframework.security.web.csrf.CsrfToken csrfToken
@param java.util.List<in.neuw.passkey.models.CredentialRecordDto> passkeys
@param String csrfHeader
@param String contextPath

@template.layouts.commonLayout(username = username, title = "Profile", content = @`
    <script>
        const csrfHeader = JSON.parse("${csrfHeader}");
        const contextPath = "${contextPath}";
    </script>
    <div class="mt-8 flow-root">
        <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
            <div class="min-w-full py-2 align-middle sm:px-6 lg:px-8 space-y-4">
                <div role="alert" class="flex justify-end items-end gap-2 alert alert-info shadow-2xl" id="success" style="display: none;">
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-6 w-6 shrink-0 stroke-current"
                            fill="none"
                            viewBox="0 0 24 24">
                        <path
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                stroke-width="2"
                                d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                    <span id="success-message">Error!</span>
                    <div class="flex justify-end items-end gap-2">
                        <button class="btn btn-sm btn-ghost" id="close-success">&#x2715;</button>
                    </div>
                </div>
                <div role="alert" class="flex justify-end items-end gap-2 alert alert-error shadow-2xl" id="error" style="display: none;">
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-6 w-6 shrink-0 stroke-current"
                            fill="none"
                            viewBox="0 0 24 24">
                        <path
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                stroke-width="2"
                                d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                    <span id="error-message">Error!</span>
                    <div class="flex justify-end items-end gap-2">
                        <button class="btn btn-sm btn-ghost" id="close-error">&#x2715;</button>
                    </div>
                </div>
                <div class="flex justify-end gap-2">
                    <%--<div class="relative w-full max-w-md">--%>
                    <div role="tablist" class="tabs tabs-lifted w-full">
                        <input type="radio" name="profile_tabs" role="tab" class="tab" aria-label="Profile" />
                        <div role="tabpanel" class="tab-content bg-base-100 border-base-300 rounded-box p-6">
                            Profile
                            <div class="flex flex-col gap-4 w-full lg:w-1/2">
                                <label class="input input-bordered flex items-center gap-2">
                                    <input
                                            type="text"
                                            placeholder="Username" class="grow" />
                                </label>
                                <label class="input input-bordered flex items-center gap-2">
                                    <input
                                            type="text"
                                            placeholder="Email" class="grow" />
                                </label>
                                <label class="input input-bordered flex items-center gap-2">
                                    <input
                                            type="text"
                                            placeholder="First Name" class="grow" />
                                </label>
                                <label class="input input-bordered flex items-center gap-2">
                                    <input
                                            type="text"
                                            placeholder="Last Name" class="grow" />
                                </label>
                            </div>
                        </div>
                        <input type="radio" name="profile_tabs" role="tab" class="tab" aria-label="Password" />
                        <div role="tabpanel" class="tab-content bg-base-100 border-base-300 rounded-box p-6">
                            Password Management
                        </div>

                        <input type="radio" name="profile_tabs" role="tab" checked="checked" class="tab" aria-label="Passkeys" />
                        <div role="tabpanel" class="tab-content bg-base-100 border-base-300 rounded-box p-6">
                            <%--<h1 class="pl-4 pr-4 text-2xl font-bold mb-4 break-before-column drop-shadow drop-shadow-lg">Manage Passkeys</h1>--%>
                            <div class="flex justify-start items-end gap-2 mb-4">
                                <div class="relative w-full max-w-md">
                                    <label for="label" class="block mb-2 text-sm text-slate-600 drop-shadow drop-shadow-lg">
                                        Register a new Passkey.
                                    </label>
                                    <input id="label" class="w-full bg-transparent placeholder:text-slate-400 text-slate-700 text-sm border border-slate-200 rounded-md px-3 py-2 transition duration-300 ease focus:outline-none focus:border-slate-400 hover:border-slate-300 shadow-sm focus:shadow" placeholder="Label of the new Passkey." />
                                </div>
                                <div>
                                    <button id="register" type="button" data-ripple-light="true" class="rounded-md bg-blue-600 py-2 px-4 border border-transparent text-center text-sm text-white transition-all shadow-md hover:shadow-lg focus:bg-blue-700 focus:shadow-none active:bg-blue-700 hover:bg-blue-700 active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none ml-2">
                                        Submit
                                    </button>
                                </div>
                            </div>
                            <script type="text/javascript" src="js/webauthn.js"></script>
                            <div id="passkeys-container">
                                @template.pages.passkeys(username = username, csrfToken = csrfToken, passkeys = passkeys, csrfHeader = csrfHeader, contextPath = contextPath)
                            </div>
                        </div>
                    </div>
                    <%--<div class="relative w-full max-w-md">
                        <label for="label" class="block mb-2 text-sm text-slate-600">
                            Name of new Passkey.
                        </label>
                        <input id="label" class="w-full bg-transparent placeholder:text-slate-400 text-slate-700 text-sm border border-slate-200 rounded-md px-3 py-2 transition duration-300 ease focus:outline-none focus:border-slate-400 hover:border-slate-300 shadow-sm focus:shadow" placeholder="Type here..." />
                    </div>
                    <div>
                        <button id="register" type="button" data-ripple-light="true" class="rounded-md bg-blue-600 py-2 px-4 border border-transparent text-center text-sm text-white transition-all shadow-md hover:shadow-lg focus:bg-blue-700 focus:shadow-none active:bg-blue-700 hover:bg-blue-700 active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none ml-2">
                            Submit
                        </button>
                    </div>--%>
                </div>
                <script type="text/javascript">
                    const ui = {
                        getRegisterButton: function() {
                            return document.getElementById('register')
                        },
                        getSuccess: function() {
                            return document.getElementById('success')
                        },
                        closeSuccess: function() {
                            return document.getElementById('close-success')
                        },
                        getSuccessMessage: function() {
                            return document.getElementById('success-message')
                        },
                        getError: function() {
                            return document.getElementById('error')
                        },
                        closeError: function() {
                            return document.getElementById('close-error')
                        },
                        getErrorMessage: function() {
                            return document.getElementById('error-message')
                        },
                        getLabelInput: function() {
                            return document.getElementById('label')
                        },
                        getDeleteForms: function() {
                            return Array.from(document.getElementsByClassName("delete-form"))
                        },
                    }
                    document.addEventListener("DOMContentLoaded",() => {
                        setupRegistration(csrfHeader, contextPath, ui)
                    });
                </script>
            </div>
        </div>
    </div>
`, csrfToken = csrfToken)