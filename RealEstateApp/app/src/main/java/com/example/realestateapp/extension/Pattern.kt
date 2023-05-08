package com.example.realestateapp.extension

internal val EMAIL_ADDRESS: Regex by lazy { "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,18}".toRegex() }

internal val PASSWORD: Regex by lazy { "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\$@\$!#%*?&.,])[A-Za-z\\d\$@\$!#%*?&.,]{8,}".toRegex() }
