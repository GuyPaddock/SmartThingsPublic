/**
 *  Guy&#39;s First App
 *
 *  Copyright 2017 Guy Paddock
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Color Temperature Switcher",
    namespace: "GuyPaddock",
    author: "Guy Paddock",
    description: "Provides fine-grained control over the color temperature of one or more bulbs.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Configuration") {
    	input name: "temperature", type: "number", title: "Temperature", description: "Desired color temperature", range: "1700..9500", required: true, displayDuringSetup: true
    	input "bulbs", "capability.colorTemperature", multiple: true
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	updateBulbs()
}

def updateBulbs() {
	log.debug("Changing color temperature of all bulbs to: ${temperature}")

	printBulbsStatus();

	for (bulb in bulbs) {
		bulb.setColorTemperature(temperature)
    }
    
	runIn(2, printBulbsStatus)
}

def printBulbsStatus() {
	log.debug("Status of all bulbs is as follows:")
    
	for (bulb in bulbs) {
        log.debug("  - ${bulb.currentState("colorTemperature").getValue()}")
    }

	log.debug("")
}