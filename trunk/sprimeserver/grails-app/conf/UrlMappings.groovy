class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }

        "/$action" (controller: "application")
        "/" (controller: "application", action:"index")
        "500"(view:'/error')
    }
}
