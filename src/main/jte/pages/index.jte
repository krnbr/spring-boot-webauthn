@param String username

@template.layouts.mtLayout(title = "index", content = @`
<section class="container mx-auto px-8 py-10 lg:py-28">
    <h2 class="block antialiased tracking-normal font-sans font-semibold text-blue-gray-900 !text-2xl !leading-snug lg:!text-3xl">
        Welcome ${username}
    </h2>
    <div class="divider"></div>
    <h6>This is a protected Page</h6>
    <p
            class="block antialiased font-sans text-xl leading-relaxed text-inherit mt-2 max-w-lg !font-normal !text-gray-500"
    >
        We're constantly trying to express ourselves and actualize our dreams. It is a work in progress.
    </p>
    <div class="divider"></div>
    <a href="/profile" class="rounded-md border border-slate-300 py-2 px-4 text-center text-sm transition-all shadow-sm hover:shadow-lg text-slate-600 hover:text-white hover:bg-slate-800 hover:border-slate-800 focus:text-white focus:bg-slate-800 focus:border-slate-800 active:border-slate-800 active:text-white active:bg-slate-800 disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none">Manage Passkeys</a>
</section>
`)