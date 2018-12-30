package com.dev.sigrid.likemindedserver.service

import com.dev.sigrid.likemindedserver.domain.Address
import com.dev.sigrid.likemindedserver.domain.Event
import com.dev.sigrid.likemindedserver.repository.CategoryRepository
import com.dev.sigrid.likemindedserver.repository.EventRepository
import com.dev.sigrid.likemindedserver.repository.GroupRepository
import spock.lang.Specification

class EventServiceTest extends Specification {

    private EventRepository eventRepository
    private CategoryRepository categoryRepository
    private GroupRepository groupRepository
    private EventService eventService

    def "setup"() {
        eventRepository = Mock(EventRepository.class)
        categoryRepository = Mock(CategoryRepository.class)
        groupRepository = Mock(GroupRepository.class)

        eventService = new EventService(eventRepository, categoryRepository, groupRepository)
    }

    def "getAllEvents returns events with empty address if there is none saved"() {
        given:
            Event event1 = new Event(
                    name: "Event1",
                    description: "a description",
                    address: null
            )
            Event event2 = new Event(
                    name: "Event2",
                    description: "another description",
                    address: new Address(addressLine: "12 some st", city: "some city", postcode: "12345", countrycode: "est")
            )
            List<Event> events = [event1, event2]
            eventRepository.findAll() >> { return events }
        when:
            def result = eventService.getAllEvents()
        then:
            result.size() == 2
            result.get(0).address.addressLine == ""
            result.get(0).address.city == ""
            result.get(0).address.postcode == ""
            result.get(0).address.countrycode == ""
            result.get(1).address.addressLine == "12 some st"
            result.get(1).address.postcode == "12345"
    }

}
