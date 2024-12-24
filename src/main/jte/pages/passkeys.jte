@import gg.jte.support.ForSupport

@param String username
@param org.springframework.security.web.csrf.CsrfToken csrfToken
@param java.util.List<in.neuw.passkey.models.CredentialRecordDto> passkeys
@param String csrfHeader
@param String contextPath

<table class="w-full text-left table-auto table-zebra shadow-md rounded-lg" id="passkeys-table">
    <thead>
    <tr>
        <%--<th class="p-4 border-b border-slate-300 bg-slate-50">
            <p class="block text-sm font-normal leading-none text-slate-500">
                Count
            </p>
        </th>--%>
        <th class="p-4 border-b border-slate-300 bg-slate-50">
            <p class="block text-sm font-normal leading-none text-slate-500">
                Label
            </p>
        </th>
        <th class="p-4 border-b border-slate-300 bg-slate-50">
            <p class="block text-sm font-normal leading-none text-slate-500">
                Created
            </p>
        </th>
        <th class="p-4 border-b border-slate-300 bg-slate-50">
            <p class="block text-sm font-normal leading-none text-slate-500">
                Last Used
            </p>
        </th>
        <th class="p-4 border-b border-slate-300 bg-slate-50">
            <p class="block text-sm font-normal leading-none text-slate-500">
                Signature Count
            </p>
        </th>
        <th class="p-4 border-b border-slate-300 bg-slate-50">
            <p class="block text-sm font-normal leading-none text-slate-500">
                Delete
            </p>
        </th>
    </tr>
    </thead>
    <tbody>
    @for(var entryLoop : ForSupport.of(passkeys))
        <tr class="hover:bg-slate-50">
            <%--<td class="p-4 border-b border-slate-200 py-5">
                <p class="block font-semibold text-sm text-slate-800">${entryLoop.getIndex()+1}</p>
            </td>--%>
            <td class="p-4 border-b border-slate-200 py-5">
                <p class="block font-semibold text-sm text-slate-800">${entryLoop.get().getLabel()}</p>
            </td>
            <td class="p-4 border-b border-slate-200 py-5">
                <p class="text-sm text-slate-500">${entryLoop.get().getCreated()}</p>
            </td>
            <td class="p-4 border-b border-slate-200 py-5">
                <p class="text-sm text-slate-500">${entryLoop.get().getLastUsed()}</p>
            </td>
            <td class="p-4 border-b border-slate-200 py-5">
                <p class="text-sm text-slate-500">${entryLoop.get().getSignatureCount()}</p>
            </td>
            <td class="p-4 border-b border-slate-200 py-5">
                <form class="delete-form no-margin" method="post" action="${contextPath}/webauthn/register/${entryLoop.get().getId()}">
                    <input type="hidden" name="method" value="delete">
                    <input type="hidden" name="${csrfToken.getParameterName()}" value="${csrfToken.getToken()}">
                    <%--<button class="btn btn-ghost" type="submit">Delete</button>--%>
                    <button type="submit" class="btn btn-sm btn-circle btn-outline">
                        <svg
                                xmlns="http://www.w3.org/2000/svg"
                                class="h-6 w-6"
                                fill="none"
                                viewBox="0 0 24 24"
                                stroke="currentColor">
                            <path
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    stroke-width="2"
                                    d="M6 18L18 6M6 6l12 12" />
                        </svg>
                    </button>
                </form>
            </td>
        </tr>
    @endfor
    </tbody>
</table>
