/*
 * flutterandroidprinter
 * Created by Andrey U.
 * 
 * Copyright (c) 2020. All rights reserved.
 * See LICENSE for distribution and usage details.
 */

class SunmiAlign {
  const SunmiAlign._internal(this.value);
  final int value;
  static const left = SunmiAlign._internal(0);
  static const center = SunmiAlign._internal(1);
  static const right = SunmiAlign._internal(2);
}

class SunmiSize {
  const SunmiSize._internal(this.value);
  final int value;
  static const xs = SunmiSize._internal(14);
  static const xs15 = SunmiSize._internal(15);
  static const xs16 = SunmiSize._internal(16);
  static const xs17 = SunmiSize._internal(17);
  static const sm = SunmiSize._internal(18);
  static const sm20 = SunmiSize._internal(20);
  static const sm22 = SunmiSize._internal(22);
  static const md = SunmiSize._internal(24);
  static const md26 = SunmiSize._internal(26);
  static const md28 = SunmiSize._internal(28);
  static const lg = SunmiSize._internal(36);
  static const xl = SunmiSize._internal(42);
}
