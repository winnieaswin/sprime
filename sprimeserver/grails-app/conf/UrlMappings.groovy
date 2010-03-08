class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }

        "/"(controller: "application" )
        "/$action" (controller: "application")
        "500"(view:'/error')
    }
}
