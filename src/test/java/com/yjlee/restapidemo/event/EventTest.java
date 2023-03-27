package com.yjlee.restapidemo.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void builder() {
        Event event = Event.builder().build();
        assertNotNull(event);
    }

    @Test
    @DisplayName("무료 여부 조건 테스트")
    void testFree() {
        assertIsFree(0, 0, true);
        assertIsFree(100, 0, false);
        assertIsFree(0, 100, false);
    }

    private void assertIsFree(int basePrice, int maxPrice, boolean isFreeExpected) {
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();
        event.update();
        assertEquals(isFreeExpected, event.isFree());
    }

    @Test
    @DisplayName("오프라인 여부 조건 테스트")
    void testOffline() {
        assertIsOffline("감남역 네이버 D2 스타트업 팩토리", true);
        assertIsOffline("", false);
    }

    private void assertIsOffline(String location, boolean isOfflineExpected) {
        Event event = Event.builder()
                .location(location)
                .build();
        event.update();
        assertEquals(isOfflineExpected, event.isOffline());
    }

}