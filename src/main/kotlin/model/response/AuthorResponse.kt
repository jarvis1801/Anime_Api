package com.jarvis.acg.api.model.response

import com.jarvis.acg.api.kmongo.model.core.Author
import com.jarvis.acg.api.model.response.base.PersonResponse

class AuthorResponse(author: Author) : PersonResponse(author)