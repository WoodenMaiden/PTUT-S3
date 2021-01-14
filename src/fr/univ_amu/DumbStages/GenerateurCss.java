package fr.univ_amu.DumbStages;

import java.io.FileWriter;
import java.io.IOException;

public class GenerateurCss {

    static public String script;
    static public void css()throws IOException {
        script = "html, body {\n" +
                    "    margin: 0;\n" +
                    "    padding: 0;\n" +
                    "    font-family: 'Work Sans', sans-serif;\n" +
                    "    font-weight: 300;\n" +
                    "    width: 100%;\n" +
                    "    position: relative;\n" +
                    "    background-color: #F0F0F0;\n" +
                    "    color: #13163E;\n" +
                    "}\n" +
                    "\n" +
                    "html {\n" +
                    "    width: auto;\n" +
                    "    margin: 0;\n" +
                    "    min-height: 100%;\n" +
                    "    top: 0;\n" +
                    "    right: 0;\n" +
                    "    left: 0;\n" +
                    "    display: flex;\n" +
                    "}\n" +
                    "\n" +
                    "header {\n" +
                    "    flex-direction: row;\n" +
                    "    height: 60px;\n" +
                    "    display: flex;\n" +
                    "    width: 100%;\n" +
                    "    background-color: white;\n" +
                    "    border: 0px;\n" +
                    "    border-bottom: 1px;\n" +
                    "    border-style: solid;\n" +
                    "    justify-content: space-between;\n" +
                    "    border-color: #F0F0F0;\n" +
                    //"     box-shadow: 0px 0px 50px 0px rgba(0,0,0,0.1);\n" +
                    "}\n" +
                    "\n" +
                    "h1 {\n" +
                    "    height: 60px;\n" +
                    "    line-height: 60px;\n" +
                    "    width: 200px;\n" +
                    "    margin: 0;\n" +
                    "    font-family: 'Poppins', sans-serif;\n" +
                    "    background-color: white;\n" +
                    "    font-size: 25px;\n" +
                    "    margin-left: 5%;\n" +
                    "}\n" +
                    "\n" +
                    "h1::after {\n" +
                    "    content: 'Stage';\n" +
                    "    font-weight: 400;\n" +
                    "}\n" +
                    "\n" +
                    "h2 {\n" +
                    "    font-weight: 400;\n" +
                    "    font-size: 15px;\n" +
                    "    width: auto;\n" +
                    "    font-family: 'Poppins', sans-serif;\n" +
                    "    line-height: 60px;\n" +
                    "    height: auto;\n" +
                    "    margin: 0;\n" +
                    "    padding: 0;\n" +
                    "    margin-right: 5%;\n" +
                    "}\n" +
                    "\n" +
                    "h3 {\n" +
                    "    float: left;\n" +
                    "    padding-bottom: 20px;\n" +
                    "    padding-top: 20px;\n" +
                    "    font-size: 20px;\n" +
                    "    background-color: #13163E;\n" +
                    "    height: 10px;\n" +
                    "    font-size: 16px;\n" +
                    "    color: white;\n" +
                    "    text-align: center;\n" +
                    "    line-height: 10px;\n" +
                    "    font-weight: 400;\n" +
                    "    width: 475px;\n" +
                    "    border-radius: 25px;\n" +
                    "}\n" +
                    "\n" +
                    "table {\n" +
                    "    align-self: center;\n" +
                    "    border-style: none;\n" +
                    "    border-top-left-radius: 50px;\n" +
                    "    border-collapse: collapse;\n" +
                    "    border-color: #13163E;\n" +
                    "    -webkit-animation: slide-top 0.8s ease-out both;\n" +
                    "    animation: slide-top 0.8s ease-out both;\n" +
                    "    margin-bottom: 50px;\n" +
                    "}\n" +
                    "\n" +
                    "th {\n" +
                    "    border-spacing: 0px;\n" +
                    "    font-weight: 400;\n" +
                    "    color: white;\n" +
                    "    background-color: #13163E;\n" +
                    "}\n" +
                    "\n" +
                    "#thLeft {\n" +
                    "    border-top-left-radius: 20px;\n" +
                    "    border: 0px;\n" +
                    "}\n" +
                    "\n" +
                    "#thRight {\n" +
                    "    border-top-right-radius: 20px;\n" +
                    "    border: 0px;\n" +
                    "}\n" +
                    "\n" +
                    "td {\n" +
                    "    background-color: white;\n" +
                    "    border: 1px;\n" +
                    "    border-style: solid;\n" +
                    "    border-color: #F0F0F0;\n" +
                    "}\n" +
                    "\n" +
                    "td:hover {\n" +
                    "    background-color: #F4F4F4;\n" +
                    "    transition-duration: 100ms;\n" +
                    "    cursor: pointer;\n" +
                    "}\n" +
                    "\n" +
                    "div {\n" +
                    "    margin-top: 50px;\n" +
                    "    margin-bottom: 50px;\n" +
                    "    display: flex;\n" +
                    "    width: 100%;\n" +
                    "    justify-content: center;\n" +
                    "}\n" +
                    //"\n" +
                    //"tbody {\n" +
                    //"    -webkit-box-shadow: 0px 0px 50px 0px rgba(0,0,0,0.1);\n" +
                    //"    -moz-box-shadow: 0px 0px 50px 0px rgba(0,0,0,0.1);\n" +
                    //"     box-shadow: 0px 0px 50px 0px rgba(0,0,0,0.1);\n" +
                    //"}\n" +
                    "\n" +
                    "footer {\n" +
                    "    position: absolute;\n" +
                    "    width: 100%;\n" +
                    //"    -webkit-box-shadow: 0px 0px 50px 0px rgba(0,0,0,0.1);\n" +
                    //"    -moz-box-shadow: 0px 0px 50px 0px rgba(0,0,0,0.1);\n" +
                    //"     box-shadow: 0px 0px 50px 0px rgba(0,0,0,0.1);\n" +
                    "    height: 50px;\n" +
                    "    display: flex;\n" +
                    "    justify-content: space-between;\n" +
                    "    bottom: 0;\n" +
                    "    border: 0px;\n" +
                    "    border-top: 1px;\n" +
                    "    border-bottom: 0px;\n" +
                    "    align-content: center;\n" +
                    "    border-style: solid;\n" +
                    "    background-color: #FFFFFF;\n" +
                    "    border-color: #F0F0F0;\n" +
                    "}\n" +
                    "\n" +

                    "p {\n" +
                    "    margin-left: 5%;\n" +
                    "    margin-right: 5%;\n" +
                    "}\n" +
                    "\n" +
                    "@-webkit-keyframes slide-top {\n" +
                    "    0% {\n" +
                    "        opacity: 0%;\n" +
                    "        -webkit-transform: translateY(150px);\n" +
                    "        transform: translateY(150px);\n" +
                    "    }\n" +
                    "    50% {\n" +
                    "        opacity: 100%;\n" +
                    "    }\n" +
                    "    100% {\n" +
                    "        -webkit-transform: translateY(0px);\n" +
                    "        transform: translateY(0px);\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "@keyframes slide-top {\n" +
                    "    0% {\n" +
                    "        opacity: 0%;\n" +
                    "        -webkit-transform: translateY(100px);\n" +
                    "        transform: translateY(100px);\n" +
                    "    }\n" +
                    "    50% {\n" +
                    "        opacity: 100%;\n" +
                    "    }\n" +
                    "    100% {\n" +
                    "        -webkit-transform: translateY(0px);\n" +
                    "        transform: translateY(0px);\n" +
                    "    }\n" +
                    "}";
        }
    }

