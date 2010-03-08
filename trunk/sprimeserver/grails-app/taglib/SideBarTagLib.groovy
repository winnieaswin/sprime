class SideBarTagLib {

    def displaySideBar = {attrs, body ->
        out << render(template: '/common/sideBar')

    }

}
