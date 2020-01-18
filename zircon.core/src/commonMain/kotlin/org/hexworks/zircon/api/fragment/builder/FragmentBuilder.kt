package org.hexworks.zircon.api.fragment.builder

import org.hexworks.zircon.api.builder.Builder

interface FragmentBuilder<T, SELF: FragmentBuilder<T, SELF>>: Builder<T>
