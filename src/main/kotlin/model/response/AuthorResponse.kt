package com.jarvis.anime.api.model.response

import com.jarvis.anime.api.kmongo.model.core.Author
import com.jarvis.anime.api.model.response.base.PersonResponse

class AuthorResponse(author: Author) : PersonResponse(author)