package dk.easv.bll.helpers;

public enum ViewType {
    MAIN {
        @Override
        public String getFXMLView() {
            return "views/main-view.fxml";
        }
    },

    ADD_EVENT {
        @Override
        public String getFXMLView() {
            return "views/add-event-view.fxml";
        }
    },

    ASSIGN_CUSTOMER {
        @Override
        public String getFXMLView() {
            return "views/addCustomerView.fxml";
        }
    },

    LOGIN {
        @Override
        public String getFXMLView() {
            return "views/Login.fxml";
        }
    },
    TICKET_VIEW {
        @Override
        public String getFXMLView() {
            return "views/ticket-view.fxml";
        }
    },

    UPCOMING_EVENT {
        @Override
        public String getFXMLView() {
            return "views/upcoming-event-view.fxml";
        }
    },
    DISPLAY_TICKETS {
        @Override
        public String getFXMLView(){
            return "views/display-tickets-view.fxml";
        }
    };



    public abstract String getFXMLView();
}
