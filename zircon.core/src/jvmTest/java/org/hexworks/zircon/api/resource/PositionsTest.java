package org.hexworks.zircon.api.resource;

import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.Component;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Position3D;
import org.hexworks.zircon.api.data.Size;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionsTest {

    private static Position POSITION_2X3 = Position.create(2, 3);
    private static Position3D POSITION3D_4X3X2 = Position3D.create(4, 3, 2);
    private static Size SIZE_3X4 = Size.create(3, 4);

    private Component componentStub;

    @Before
    public void setUp() {
        componentStub = Components.label()
                .withPosition(POSITION_2X3)
                .withSize(SIZE_3X4)
                .build();
    }

    @Test
    public void shouldProperlyReturnTopLeftCorner() {
        assertThat(Position.topLeftCorner()).isEqualTo(Position.create(0, 0));
    }

    @Test
    public void shouldProperlyReturnOffset1x1() {
        assertThat(Position.offset1x1()).isEqualTo(Position.create(1, 1));
    }

    @Test
    public void shouldProperlyReturnZero() {
        assertThat(Position.zero()).isEqualTo(Position.create(0, 0));
    }

    @Test
    public void shouldProperlyReturnDefaultPosition() {
        assertThat(Position.defaultPosition()).isEqualTo(Position.create(0, 0));
    }

    @Test
    public void shouldProperlyReturnUnknown() {
        assertThat(Position.unknown()).isEqualTo(Position.create(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void shouldProperlyReturnTopLeftOf() {
        assertThat(Position.topLeftOf(componentStub)).isEqualTo(POSITION_2X3);
    }

    @Test
    public void shouldProperlyReturnTopRightOf() {
        assertThat(Position.topRightOf(componentStub)).isEqualTo(POSITION_2X3.withRelativeX(SIZE_3X4.getWidth()));
    }

    @Test
    public void shouldProperlyReturnBottomLeftOf() {
        assertThat(Position.bottomLeftOf(componentStub)).isEqualTo(POSITION_2X3.withRelativeY(SIZE_3X4.getHeight()));
    }

    @Test
    public void shouldProperlyReturnBottomRightOf() {
        assertThat(Position.bottomRightOf(componentStub)).isEqualTo(POSITION_2X3.plus(SIZE_3X4.toPosition()));
    }

    @Test
    public void shouldProperlyCrate() {
        assertThat(Position.create(2, 3)).isEqualTo(POSITION_2X3);
    }

    @Test
    public void shouldProperlyReturnDefault3DPosition() {
        assertThat(Position3D.defaultPosition()).isEqualTo(Position3D.defaultPosition());
    }

    @Test
    public void shouldProperlyCreate3DPosition() {
        assertThat(Position3D.create(4, 3, 2)).isEqualTo(POSITION3D_4X3X2);
    }

    @Test
    public void shouldProperlyConvert2DTo3DPosition() {
        assertThat(POSITION_2X3.to3DPosition(4)).isEqualTo(Position3D.create(2, 3, 4));
    }

}
