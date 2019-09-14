package org.hexworks.zircon.api.resource;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.data.impl.Position3D;
import org.hexworks.zircon.internal.component.impl.ComponentStub;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionsTest {

    private static Position POSITION_2X3 = Positions.create(2, 3);
    private static Position3D POSITION3D_4X3X2 = Positions.create3DPosition(4, 3, 2);
    private static Size SIZE_3X4 = Sizes.create(3, 4);

    private ComponentStub componentStub;

    @Before
    public void setUp() {
        componentStub = ComponentStub.create(POSITION_2X3, SIZE_3X4);
    }

    @Test
    public void shouldProperlyReturnTopLeftCorner() {
        assertThat(Positions.topLeftCorner()).isEqualTo(Positions.create(0, 0));
    }

    @Test
    public void shouldProperlyReturnOffset1x1() {
        assertThat(Positions.offset1x1()).isEqualTo(Positions.create(1, 1));
    }

    @Test
    public void shouldProperlyReturnZero() {
        assertThat(Positions.zero()).isEqualTo(Positions.create(0, 0));
    }

    @Test
    public void shouldProperlyReturnDefaultPosition() {
        assertThat(Positions.defaultPosition()).isEqualTo(Positions.create(0, 0));
    }

    @Test
    public void shouldProperlyReturnUnknown() {
        assertThat(Positions.unknown()).isEqualTo(Positions.create(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void shouldProperlyReturnTopLeftOf() {
        assertThat(Positions.topLeftOf(componentStub)).isEqualTo(POSITION_2X3);
    }

    @Test
    public void shouldProperlyReturnTopRightOf() {
        assertThat(Positions.topRightOf(componentStub)).isEqualTo(POSITION_2X3.withRelativeX(SIZE_3X4.getWidth()));
    }

    @Test
    public void shouldProperlyReturnBottomLeftOf() {
        assertThat(Positions.bottomLeftOf(componentStub)).isEqualTo(POSITION_2X3.withRelativeY(SIZE_3X4.getHeight()));
    }

    @Test
    public void shouldProperlyReturnBottomRightOf() {
        assertThat(Positions.bottomRightOf(componentStub)).isEqualTo(POSITION_2X3.plus(SIZE_3X4.toPosition()));
    }

    @Test
    public void shouldProperlyCrate() {
        assertThat(Positions.create(2, 3)).isEqualTo(POSITION_2X3);
    }

    @Test
    public void shouldProperlyReturnDefault3DPosition() {
        assertThat(Positions.default3DPosition()).isEqualTo(Positions.default3DPosition());
    }

    @Test
    public void shouldProperlyCreate3DPosition() {
        assertThat(Positions.create3DPosition(4, 3, 2)).isEqualTo(POSITION3D_4X3X2);
    }

    @Test
    public void shouldProperlyConvert2DTo3DPosition() {
        assertThat(Positions.from2DTo3D(POSITION_2X3, 4)).isEqualTo(Positions.create3DPosition(2, 3, 4));
    }

}
