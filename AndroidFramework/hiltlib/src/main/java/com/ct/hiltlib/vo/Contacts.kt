package com.ct.hiltlib.vo

import javax.inject.Inject

data class Contacts @Inject constructor(val name: String)